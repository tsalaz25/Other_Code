%Tomas Salaz Math/CS375 Numerical Methods
%HW3 due 10/5

%% (a) Use Newtons Metod to Approx x^3=x^2+x+1 within 1e^-8
% Based on Graph,
% -Root is <2
% -x0 needs to be >1 for a valid test

bf = @(x) x.^3 - x.^2 - x - 1;  %BaseFunct, Looking for Root
df = @(x) 3*x.^2 - 2*x - 1;     %DerivitiveFunct for Method   
x0  = 1.5;                      %Initial Guess
tol = 1e-8;                     %Tolerance
max_i = 10;                    %Max Iterations

%%Quick Tests foe Bisectino Method (Comment out Lines 9-11 or 16-18)
%bf = @(x) (2*x)/(1+x.^2)-atan(x);
%df = @(x) (1-3*x^2) / ((1+x^2)^2);
%x0 = 1 ;

arr = zeros(1,max_i);
x = x0;

%Method Implementation
for i = 1: max_i
    %intitial cases
    fx = bf(x);
    dfx = df(x);
    %Check Div by 0
    if dfx == 0
        print("Method Failed");
    end
    %Update the Iterations and Check Tolerance
    xi = x - fx/dfx;
    arr(i) = xi;
    if abs(x - xi) < tol
        fprintf("Converged to x = %.8f in %d iterations\n", ...
        xi, i);
        break;
    end
    x = xi;
    %Check for Runaway Case
    if i == max_i
        fprintf("No Convergence");
    end
end

figure;
title("Newtons Method Problem 1");
fprintf('%.8f\n',arr);
xbound = linspace(-2,2.5,1000);
xline(0, '-black', LineWidth=.5);
yline(0, '-black',LineWidth=.5); hold on;
vals = arrayfun(bf,xbound);
plot(xbound,vals);
method_y = bf(arr);
plot(arr,method_y,'bo', 'MarkerSize', 3 , 'MarkerFaceColor', 'b');
xlim(-2,2.5);
axis equal;