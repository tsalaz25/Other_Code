%Sample script for Newton's Method
%Solving f(x)=0, with starting initial guess x_o
%Method: x_{n+1}=x_{n}-f(x_n)/f'(x_n+1)

clc
clear all
format long
n_max=20;
tol=10^(-6);
%initial guess
xold=0;

formatSpec = '%d, %d, %d';
for n=1:n_max
    
    [fx,fprimex]=function1(xold);
    %print
    sprintf(formatSpec,n-1,xold,fx)
    
    xapprox(n)=xold;
    yapprox(n)=fx;
    
    xnew=xold-(fx)/(fprimex);
    
    
    %stopping criteria
     if abs(fx)<tol
        break
    end
    
    %update
    xold=xnew;
    
end

x=[0:.1:2];y=function1(x);
figure(2);
plot(x,y,'b--')
hold on
plot(xapprox,yapprox,'o')
grid on;