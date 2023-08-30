package com.language.mylang;

import java.util.List;

interface QweCallable {

    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
