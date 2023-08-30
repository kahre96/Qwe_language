package com.language.mylang;

import java.util.List;

class QweFunctions implements QweCallable{

    private final ASTNode.Function declaration;

    QweFunctions(ASTNode.Function declaration){
        this.declaration= declaration;

    }

    @Override
    public int arity() {
        return declaration.parameters.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(interpreter.global);

        for(int i = 0; i<declaration.parameters.size(); i++){
            environment.define(declaration.parameters.get(i).lexeme,arguments.get(i));
        }

        try{
            interpreter.executeBlock(declaration.body,environment);
        } catch(Return returnValue){
            return returnValue.value;
        }


        return null;
    }
}
