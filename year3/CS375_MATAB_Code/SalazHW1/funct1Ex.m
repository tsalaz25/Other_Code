function [y,dy] = funct1(x)
%FUNCT1 Function and Deriv for an equation, Y.
y=(x+1).^2-exp(x)-1;
dy = 2*(x+1)-exp(x);
end

