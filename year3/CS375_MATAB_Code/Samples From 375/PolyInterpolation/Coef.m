function [a] = Coef(n,x,y)
%Coefficients for Interpolation

clear a;

for i=0:n
    a(i+1)=y(i+1);
end

for j=1:n
    for i=n:-1:j
        a(i+1)=(a(i+1)-a(i+1-1))/(x(i+1)-x(i+1-j));
    end
end

end