%Tomas Salaz Math/CS375 Numerical Methods
%HW7 due 11/26

%% Quick Calcualtion for 3B

function T_N = trap_rule(N)
    f = @(x) sin(x);    % Define the function
    a = 0; b = pi;      % Boundaries
    h = (b - a) / N;    % Spacinh
    
    % Trapezoidal Rule
    T_N = 0.5 * (f(a) + f(b));
    for i = 1:(N-1)
        T_N = T_N + f(a + i * h);
    end
    
    T_N = T_N * h;
end

N = 161;    %Number of Subintervals from 3A
T = trap_rule(N);
fprintf('T(161) = %.15f\n', T);