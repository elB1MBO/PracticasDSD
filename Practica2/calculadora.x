struct inputs{
    float num1;
    float num2;
    char operador;
};

program CALCULADORAPROG{
    version CALCULADORA{
        float SUMA(inputs)=1;
        float RESTA(inputs)=2;
        float MUL(inputs)=3;
        float DIV(inputs)=4;
    }=1;
}=0x2fffffff;