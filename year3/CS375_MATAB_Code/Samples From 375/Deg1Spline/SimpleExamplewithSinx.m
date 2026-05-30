%Basic example for approximating y=sin(x) on [0,2pi]
%sample script for making use of the spline-degree1 function
clear all All
%interval length [a,b]
a=0;
b=2*pi;
%number of equally spaced nodes
n=1000;  %try different values 10,20,100
%spacing
h=(b-a)/(n-1);

t_knots=[a:h:b];
y_table_values=sin(t_knots);

%call function, X=evaluation of an interval or point
X=[a:h/5:b];    %choose a finer grid to plot points in between knots
Y_approx=spline_degree1(t_knots,y_table_values,X);

figure(1);
plot(X,Y_approx,'o-',t_knots,y_table_values,'rx')
legend('Spline_1','Equally spaced knots')
title(['n=',num2str(n)])