package ditto.ast.definitions;

import ditto.ast.statements.Statement;

public abstract class Definition extends Statement {/*
    La razón por la que extiende Statement es que `DefVar`
    debe ser un Statement, aunque `DefFunc` no lo sea. Como
    en Java no se puede heredar de dos clases, he visto
    necesario hacer esta guarrada. Confio en que el problema
    de que no haya definiciones a funciones en mitad de una 
    función se arregla desde la sintaxis.
*/}
