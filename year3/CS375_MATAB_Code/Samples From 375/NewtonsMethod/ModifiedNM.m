 %Sample script for Newton's Method
%Solving f(x)=0, with starting initial guess x_o
%Method: x_{n+1}=x_{n}-f(x_n)/f'(x_n+1)

clc
clear all
format long
n_max=20;
tol=10^(-12);
%initial guess
xold=2.5; %runaway
xold=2.1; %approaches from right
%xold=2.3; %approaches from left
xold=0.8
m=2;  %to accelerate choose m=2

formatSpec = '%d, %d, %d';
for n=1:n_max
    
    [fx,fprimex]=function2(xold);
    %print
    sprintf(formatSpec,n-1,xold,fx)
    
    xapprox(n)=xold;
    yapprox(n)=fx;
    
    xnew=xold-m*((fx)/(fprimex));
    
    
    %stopping criteria
     if abs(fx)<tol
        break
    end
    
    %update
    xold=xnew;
    
end

x=[0:.1:3];y=function2(x);
figure(2);
plot(x,y,'b--')
hold on
plot(xapprox,yapprox,'o')
grid on;