service Calculadora{
    void ping(),
    double suma(1:double num1, 2:double num2),
    double resta(1:double num1, 2:double num2),
    double mult(1:double num1, 2:double num2),
    double div(1:double num1, 2:double num2),
    double pot(1:double num1, 2:double num2),
    double raiz(1:double num1),
    double fact(1:double num1),
    list<double> sumaVectores(1:list<double> v1, 2:list<double> v2),
    list<double> restaVectores(1:list<double> v1, 2:list<double> v2),
    list<double> multVectores(1:list<double> v1, 2:list<double> v2),
    list<double> divVectores(1:list<double> v1, 2:list<double> v2),
}