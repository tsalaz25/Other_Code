function [y,dy] = function2(x)
%double root, modified scheme is needed.
y = (1/1)*(x-1).^2.*sin(x);
dy= (1/1)*((x-1).^2.*cos(x)+2*sin(x).*(x-1));
end
