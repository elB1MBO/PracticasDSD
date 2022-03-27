/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"


float	//Tiene que ser float para devolver el valor
calculadoraprog_1(char *host, float n1, float n2, char op, CLIENT *clnt)
{
	//CLIENT *clnt;
	float  *result_1; //Resultado suma
	inputs suma_1_arg1;	//Datos de entrada para suma
	float  *result_2; //Resultado resta
	inputs resta_1_arg1; //Datos de entrada para resta
	float  *result_3; //Resultado multiplicacion
	inputs mul_1_arg1; //Datos entrada mult
	float  *result_4; //Resultado division
	inputs div_1_arg1; //Datos entrada div


	//Comprobamos que operacion se quiere realizar
	switch (op)
	{
	case '+':
		suma_1_arg1.num1=n1;
		suma_1_arg1.num2=n2;
		suma_1_arg1.operador=op;
		result_1 = suma_1(suma_1_arg1, clnt);
		if(result_1 == (float *) NULL)
			clnt_perror(clnt, "Error: valor de la suma es nulo");
		
		return *result_1;
		break;
	case '-':
		resta_1_arg1.num1=n1;
		resta_1_arg1.num2=n2;
		resta_1_arg1.operador=op;

		result_2 = resta_1(resta_1_arg1, clnt);
		if(result_2 == (float *) NULL)
			clnt_perror(clnt, "Error: valor de la resta es nulo");
		
		return *result_2;
		break;
	case 'x':
		mul_1_arg1.num1=n1;
		mul_1_arg1.num2=n2;
		mul_1_arg1.operador=op;

		result_3 = mul_1(mul_1_arg1, clnt);
		if(result_3 == (float *) NULL)
			clnt_perror(clnt, "Error: valor de la multiplicacion es nulo");
		
		return *result_3;
		break;
	case '/':
		div_1_arg1.num1=n1;
		div_1_arg1.num2=n2;
		div_1_arg1.operador=op;

		result_4 = div_1(div_1_arg1, clnt);
		if(result_4 == (float *) NULL)
			clnt_perror(clnt, "Error: valor de la division es nulo");
		
		return *result_4;
		break;	
	//Si no es ninguno de esos operadores, falla
	default:
		break;
	}


#ifndef	DEBUG
	clnt = clnt_create (host, CALCULADORAPROG, CALCULADORA, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	result_1 = suma_1(suma_1_arg1, clnt);
	if (result_1 == (float *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_2 = resta_1(resta_1_arg1, clnt);
	if (result_2 == (float *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_3 = mul_1(mul_1_arg1, clnt);
	if (result_3 == (float *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_4 = div_1(div_1_arg1, clnt);
	if (result_4 == (float *) NULL) {
		clnt_perror (clnt, "call failed");
	}
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;
	float a, b;
	char opr;
	CLIENT *clnt;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	a = atof(argv[2]);
	printf("OP1=%f\n",a);
	opr = argv[3][0];
	printf("Operador=%c\n",opr);
	b = atof(argv[4]);
	printf("OP2=%f\n",b);
	printf("CALCULADORA\n");
	host = argv[1];

	clnt = clnt_create (host, CALCULADORAPROG, CALCULADORA, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}

	printf("RESULTADO: %.2f\n", calculadoraprog_1(host, a, b, opr, clnt));
	
exit (0);
}