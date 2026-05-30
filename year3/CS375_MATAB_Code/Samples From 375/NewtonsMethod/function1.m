function [y,dy] = function1(x)
%funtion and deriative to be used in the basic Newton's Method example
%This problem was covered during lectures
y = (x+1).^2-exp(x)-1;
dy= 2*(x+1)-exp(x);
end

