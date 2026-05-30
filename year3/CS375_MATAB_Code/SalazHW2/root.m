%Tomas Salaz Math/CS375 Numerical Methods
%HW2 due 9/27

%% (a) 
% See attached .pdf for explanatin
% Distance between points (x,x^2) and (1,0) given by
% D = sqrt[(x-1)^2 + (x^2-0)^2]
% D^2 = (x-1)^2 + (x^2)^2 = (x-1)^2 + x^4 == x^4 + x^2 - 2x + 1
% d/dx[D^2] = d/dx[x^4 + x^2 - 2x + 1] == 4x^3 + 2x - 2
% Solving for Inflectoin Point 
% 0 = 4x^3 + 2x - 2 
% if f(x) = 0, then 
% f(x) = 2x^3 + 2x - 2

%% (b) and (c)
%Funtion and Derivitive defiend by X parameter and Params for Method
bfx = @(x) x.^2;             %Base Funnction x^2
f = @(x) 4*x.^3 + 2*x - 2;  %Funciton
df = @(x) 12*x^.2 + 2 ;     %Derivitive
x0 = 2;                     %Inital Guess
%x0 = 1; x0=2.5             %Testing Cases
tol = 1e-4;                 %Tolerance 
max_i = 100;                %Max Iterations

%Newtons Method Implementation
x = x0;
for i = 1: max_i
    %Run Initial Cases
    fx = f(x);
    dfx = df(x);
    %Check for valid Derivitive Case
    if dfx == 0
        fprintf("Failed, dfx=0");
    end
    %Assigment of Newtons Method
    xi = x - fx/dfx;

    %Check tolerance for i-th iteration
    if abs(x - xi) < tol
        fprintf("Converged to x = %.6f in %d iterations\n", xi, i);
        fprintf("f(%.6f) = %.6f", xi, abs(bfx(xi)));
        break;
    end 
    %Reassign for next Iteration
    x = xi;

    %No Convergence Catcher
    if i == max_i
        fprintf("No Convergence");
    end


end

%% (d)
%Set the Graphs X-Axis bounds and Call the funciton, stored in an Array
x_bound1 = linspace (0,2,500);
x_bound2 = linspace(-2,2,1000);
%Draw the Graph
figure;
%Plotting points of Convergence Form Newtons Method%
fun_vals = arrayfun(f,x_bound1);
%NOTE: Use abs(fun_vals) for better visual
plot(x_bound1, (fun_vals), 'b-', 'LineWidth', 2); hold on;
%Plot the f(x) = x^2 at the Result the Method Gave
fun_vals_b = arrayfun(bfx, x_bound2);
plot(x_bound2, fun_vals_b, '-r' , 'LineWidth', 2); hold on;
%Plotting Points of Intrest
plot(xi, bfx(xi) , 'ro' , 'MarkerSize', 5, 'MarkerFaceColor', 'y'); hold on;
plot(1,0 ,'bo','MarkerSize', 5, 'MarkerFaceColor', 'y'); hold on;
%Making Graph Look Good
x_range = [-1,2]; y_range = [-1,3];
xline(0, '-black', LineWidth=.5); yline(0, '-black',LineWidth=.5);
title("f(x)=4x^3+2x^2-2 w/ Newtons Method: x0=2");
legend("f(x)=2x^3+2x-2" , "g(x)=x^2","[g(0.589695),0.34774]" , "[1,0]");
grid on;
axis([x_range(1), x_range(2) ,y_range(1) , y_range(2)]);