%Tomas Salaz Math/CS375 Numerical Methods
%HW3 due 10/5

%% (c) Solve System, by eliminating y then solving for x by NM using x0=1
% x^3 -2xy + y^7 -4x^3y = 5 :: ysinx+ 3x^2 y+tanx = 4

x0 = 1;             %Initial Guess
tol = 1e-8;         %Tolerance and H for Derivitive Approx
i_max = 50;         %Max Iterations
h = 1e-8;
arr = zeros(1,max_i);  %Array for Saving Values
final_x = 0;               %Saving the Answer 

x = x0;
for i = 1: i_max
    fx = f(x);
    dfx = (f(x+h) - f(x-h)) / (2*h);
    %Error Catcher
    if dfx == 0
        fprintf("Method Failed: Div by 0");
        break;
    end
    %Save and Update
    arr(i) = x;
    xi = x - fx/dfx;
    %Check Tolerance
    if abs(x-xi)< tol
        final_x = xi;
        fprintf ("Converged to %d.8 in %d iterations\n",xi ,i);
        break;
    end
    %Update and Check Runaway 
    x = xi;
    if i == i_max
        fprintf("No Convergence");
    end
end

fprintf('%.8f\n',arr);
fprintf("Saved Value for Funtion input: %d\n\n",final_x);
f1 = f(final_x);
f2 = g(final_x);
fprintf("System\n");
fprintf("f(x)=x^3*2*x*y+y^7-4*x^3*y-5\n");
fprintf("g(x)=y*sin(x)+3*x^2*y+tan(x)-4\n");



%% Funtions for f(x)
function fx = f(x)
    y = (4-tan(x)) / (sin(x)+3*x^2);
    fx = x^3 * 2*x*y + y^7 - 4*x^3*y - 5;
end

function gx = g(x)
   y = (4-tan(x)) / (sin(x)+3*x^2);
   gx = y*sin(x) + 3*x^2*y + tan(x) - 4;  
end