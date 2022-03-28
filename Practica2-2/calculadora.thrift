//typedef i32 int Se puede usar typedef para definir nombres
service Calculadora{
    void ping(),
    double suma(1:double num1, 2:double num2),
    double resta(1:double num1, 2:double num2),
    double mult(1:double num1, 2:double num2),
    double div(1:double num1, 2:double num2),
}