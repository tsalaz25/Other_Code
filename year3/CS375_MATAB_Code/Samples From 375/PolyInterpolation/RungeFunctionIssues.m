%Runge function and interpolation issues
%
clear All all
%n pn(x) , which means that we have (n+1) nodes
n=9+40;
a=-5;
b=5;
h=(b-a)/(n);
x=[a:h:b];
y=1./(1+x.^2);
a_coeff=Coef(n,x,y);

xval=[-5:.1:5];
for i=1:length(xval)
    yval(i)=Eval(n,x,a_coeff,xval(i));
end

figure(1)
plot(x,y,'rx--')
hold on
plot(xval,yval,'bo')
plot(xval,1./(1+xval.^2),'ko')
legend('data points','interpolation','exact')
grid on
title('Runge''s phenomenon')



