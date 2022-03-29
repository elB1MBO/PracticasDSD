//typedef i32 int Se puede usar typedef para definir nombres
service Calculadora{
    void ping(),
    double suma(1:double num1, 2:double num2),
    double resta(1:double num1, 2:double num2),
    double mult(1:double num1, 2:double num2),
    double div(1:double num1, 2:double num2),
    double mod(1:double num1, 2:double num2),
    double pot(1:double num1, 2:double num2),
    double raiz(1:double num1),
    double fact(1:double num1),
}