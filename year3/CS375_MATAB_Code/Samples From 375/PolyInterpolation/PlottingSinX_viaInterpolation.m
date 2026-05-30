%Plotting Sin(x) vs the interpolating Po for Sin(x)
%using 10 equally spaced nodes.
clear All all
a=0;
b=1.6875;
nodes1=10;
h=(b-a)/(nodes1-1);
x=[a:h:b];
y=sin(x);

%polynomial interpolation built-in function
coefficients=polyfit(x,y,nodes1-1);  %compute the coeff of poly with degree (nodes-1).
P=polyval(coefficients,x);

figure(1);
plot(x,y,'k--',x,P,'ro')
legend('sin(x)','P_9(x)')
grid on


%Next task was to evaluate at 37 points
a=0;
b=1.6875;
nodes2=37;
h=(b-a)/(nodes2-1);
x2=[a:h:b];


%polynomial interpolation built-in function
P37=polyval(coefficients,x2);

figure(2);
plot(x,y,'k--',x2,P37,'ro')
legend('sin(x)','P_{9}(x)')
title('evaluation at 37 points')
grid on


figure(3);
plot(x2,sin(x2)-P37,'bx-')
legend('Error')
title('Error')
%axis([a b -10^(-6) 10^(-6)])
grid on





