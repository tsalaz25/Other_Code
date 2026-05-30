function [val] = Eval(n,x,a,t)
%EVAL from course Files

temp = a(end);
for i=n-1:-1:0
    temp= (temp)*(t-x(i+1))+a(i+1);
end

val = temp;
end