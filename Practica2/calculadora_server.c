/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"
#include <math.h>

//SUMA	
double *
suma_1_svc(double arg1, double arg2,  struct svc_req *rqstp)
{
	static double  result;

	result = arg1 + arg2;
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//RESTA
double *
resta_1_svc(double arg1, double arg2,  struct svc_req *rqstp)
{
	static double  result;

	result = arg1 - arg2;
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//MULT
double *
mul_1_svc(double arg1, double arg2,  struct svc_req *rqstp)
{
	static double  result;

	result = arg1 * arg2;
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//DIV
double *
div_1_svc(double arg1, double arg2,  struct svc_req *rqstp)
{
	static double  result;

	result = arg1 / arg2;
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//ABS
double *
abs_1_svc(double arg1,  struct svc_req *rqstp)
{
	static double  result;

	result = (double) abs(arg1);
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//POTENCIA
double *
pot_1_svc(double arg1, double arg2,  struct svc_req *rqstp)
{
	static double  result;

	result = (double) pow(arg1, arg2);
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//RAIZ
double *
raiz_1_svc(double arg1,  struct svc_req *rqstp)
{
	static double  result;

	result = sqrt(arg1);
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//FACTORIAL
double *
fact_1_svc(double arg1,  struct svc_req *rqstp)
{
	static double  result=1;

	for(double i = 1; i<=arg1; i++)
		result*=i;

	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
//LOGARITMO
double *
log_1_svc(double arg1,  struct svc_req *rqstp)
{
	static double  result;

	result = log(arg1);
	printf("Se obtuvo la solicitud, y se ha enviado la respuesta: %.2f\n", result);
	return &result;
}
