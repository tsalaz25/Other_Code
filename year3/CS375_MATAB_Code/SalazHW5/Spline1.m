function [output] = spline_degree1(t,y,X)
% t vectorrepresents the knots and y-vec contains y-values
% x is the point to be evaluated by the constructed degree 1 spline
% edited version can take vector of values [a:h:b]
clear output
n=length(t);
for j=1:length(X) %------------------ added part for taking [a,b]
x=X(j);
%---------------------------------------------original part of pseudo-code
for i=n-1:-1:1
if (x-t(i))>= 0
break;
end
end
output(j)=y(i)+(x-t(i)).*((y(i+1)-y(i))/(t(i+1)-t(i)));
%---------------------------------------------original part of pseudo-code
end %---------------------------------added part for taking [a,b]
end
