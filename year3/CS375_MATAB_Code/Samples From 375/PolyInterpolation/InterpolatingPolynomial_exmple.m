%Script for plotting the interpolating polynomial
%Table was given as  x | 1 | 3/2  | 0 | 2
%                    y | 3 | 13/4 | 3 | 5/3
%
%get coefficients
clear All all

n=3;
x=[1 3/2 0 2];
y=[3 13/4 3 5/3];
a=Coef(3,x,y);

% point=0;
% Eval(n,x,a,point)

xval=[0:.1:2];
for i=1:length(xval)
    yval(i)=Eval(n,x,a,xval(i));
end

figure(1)
plot(x,y,'rx')
hold on
plot(xval,yval,'bo')
legend('data points','interpolation')
grid on
title('Interpolating Polynomial')







