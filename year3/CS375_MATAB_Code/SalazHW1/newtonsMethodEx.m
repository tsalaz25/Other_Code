clc
format long
n_max = 20; %max iterartions
t = 10^(-6); %Tolerance
x_o = 0; %intial guess

formatSpec = '%d,%d,%d';
for n=1:n_max
    [fx,fpx] = funct1Ex(x_o);
    sprintf(formatSpec,n-1,x_o,fx)

    xapp(n) = x_o;
    yapp(n) = fx;
    xnew = x_o-(fx)/(fpx);
    %stopping critera
    if abs(fx)<t
        break
    end
    %update
    x_o = xnew;
end
%plot
x = [0:.1:2];y=(x+1).^2-exp(x)-1;
figure(2);
plot(x,y)
hold on
plot (xapp, yapp, 'o');
grid on;