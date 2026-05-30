%Tomas Salaz Math/CS375 Numerical Methods
%HW5 due 11/14

%% TASK 1) tanh(x) interpolations

%11 Nodes on Different Intervals (Match with lines 17 through 20)---------%
    x_nodes = linspace(-3,3,11);
    %x_nodes = linspace(-5,5,11);
    %x_nodes = linspace(-11,11,11);
%-------------------------------------------------------------------------%
            
% U is funtion Values at Nodes; Starting Interpolation Process
u = tanh(x_nodes);
n = length(x_nodes)-1;
a = Coef(n,x_nodes,u);       

%Define x vals for plotting ----------------------------------------------%
    x_vals = linspace(-3,3,100);
    %x_vals = linspace(-5,5,100); 
    %x_vals = linspace(-11,11,100);
%-------------------------------------------------------------------------%

%All Function Evaluations
Pn = arrayfun(@(t) Eval(n,x_nodes,a,t), x_vals);  
Spline_vals = NCS(x_nodes,u,x_vals);
tanh_vals = tanh(x_vals);


figure;
plot(x_vals,tanh_vals,'k--','DisplayName','tanh(x)','LineWidth',1); hold on;
plot(x_vals,Pn,'r-','DisplayName','Interp. Poly');
plot(x_vals,Spline_vals,'b-','DisplayName','Cubic Spline');
plot(x_nodes,u,'ko', 'DisplayName','Nodes Values', 'MarkerSize', 3);
hold off;

legend;
xlabel('x');
ylabel('y');
grid on;