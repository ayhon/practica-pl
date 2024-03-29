const { readFileSync } = require("fs");
const execSync = require('child_process').execSync;
const fs = require("fs");
const path = require("path");

/// Recibo como parametro el nombre del archivo a compilar (.wat)
/// Ejemplo: node main.js typecheck.wat
/// El script compila el archivo .wat con wat2wasm y luego ejecuta el modulo
/// Lee como entrada el fichero situado en la carpeta inputs/$name.txt de la misma carpeta que el .wat
/// E imprime el resultado en carpeta outputs/$name.txt
/// Si el archivo no existe, se crea

if (process.argv.length < 3) {
    console.log("Usage: node main.js <wat file>");
    process.exit();
}

let watFilePath = process.argv[2];

/// input esta en el mismo directorio que el .wat
/// output esta en el mismo directorio que el .wat
const watFile = path.join(__dirname, watFilePath);
const name = path.basename(watFile, ".wat");
const folder = path.dirname(watFile);

const wasmFile = path.join(folder, `${name}.wasm`);
const inputFile = path.join(folder, "input", `${name}.txt`);
const outputFile = path.join(folder, "output", `${name}.txt`);
const expectedOutputFile = path.join(folder, "expected_output", `${name}.txt`);

let entrada = [];
let i = 0;

const disableConsolePrint = process.argv.includes("--disable-console-print");

const importObjects = {
    runtime: {
        exceptionHandler: function (code) {
            let errStr;
            if (code == 1) {
                errStr = "Error 1. ";
            } else if (code == 2) {
                errStr = "Error 2. ";
            } else if (code == 3) {
                errStr = "Not enough memory. ";
            } else {
                errStr = "Unknown error\n";
            }
            throw new Error(errStr);
        },
        print: function (n) {
            if (!disableConsolePrint) {
                console.log(n);
            }
            if (outputFile) {
                fs.appendFileSync(outputFile, n + "\n");
            }
        },
        scan: function () {
            if (i >= entrada.length) {
                throw new Error("No hay mas entradas");
            }
            let val = entrada[i];
            i += 1;
            return val;
        }
    }
};

async function start(wasmFile) {
    const code = readFileSync(wasmFile);
    wasmModule = await WebAssembly.compile(code);
    instance = await WebAssembly.instantiate(wasmModule, importObjects);
    /// await instance.exports.init();
}

execSync(`./wat2wasm "${watFilePath}" -o "${wasmFile}"`, async (error, stdout, stderr) => {
    if (error) {
        console.error(`error wat2wasm: ${error.message}`);
        process.exit(1);
    }

    if (stderr) {
        console.error(`stderr wat2wasm: ${stderr}`);
        process.exit(1);
    }
});

const main = async () => {
    /// Leer los ficheros de entrada y salida
    if (fs.existsSync(inputFile)) {
        /// Cargar en memoria el fichero de entrada
        /// Partirlo por lineas
        const input = fs.readFileSync(inputFile, "utf8");
        entrada = input.split("\n");
    }

    if (!fs.existsSync(outputFile)) {
        /// Crear el fichero de salida vacio para que no de error
        fs.mkdirSync(path.dirname(outputFile), { recursive: true });
    }

    /// Vaciar el fichero de salida
    fs.writeFileSync(outputFile, "");

    try {
        await start(watFilePath.replace(".wat", ".wasm"));
    }
    catch (e) {
        console.log(e);
        process.exit(1);
    }

    if (fs.existsSync(expectedOutputFile)) {
        /// Comparar la salida con el fichero de salida esperado
        const expectedOutput = fs.readFileSync(expectedOutputFile, "utf8").trim();
        const output = fs.readFileSync(outputFile, "utf8").trim();

        if (output == expectedOutput) {
            console.log(`✅ [${name}] Coincide la salida con la esperada`);
        } else {
            console.log(`❌ [${name}] No coincide la salida con la esperada`);
            /// Show diff
            console.log("➡ Salida esperada");
            console.log(expectedOutput);
            console.log("➡ Salida obtenida");
            console.log(output);
            process.exit(1);
        }
    }

    process.exit(0);
};

main();