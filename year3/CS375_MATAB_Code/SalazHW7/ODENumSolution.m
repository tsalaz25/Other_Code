%Tomas Salaz Math/CS375 Numerical Methods
%HW7 due 11/26

%% 2: Numerical Solutions to ODE
% Relavent functions to the problem
% f(x) = u"(x) + P(x)u'(x) + Q(x)u  u(a)=alpha u(b)=beta
% 4x^2 = u"(x) + 3u'(x) + 2u(x)  u(1)=1  u(2)=6 

% Parameters of the problem
P = @(x) 3;
Q = @(x) 2;
f = @(x) 4 * x.^2;
u_exact = @(x) 7 - 6*x + 2*x.^2 + (-exp(3)*(2 + 3*exp(1))/(exp(1) - 1))*exp(-2*x) + (2*exp(1) + 3*exp(3))/(exp(1) - 1)*exp(-x);

% Boundary conditions
a = 1;b = 2;alpha = 1;beta = 6;

% For N = 10
N10= 10;
h10 = (b - a) / N10;
x10 = linspace(a, b, N10+1);   % 10 evenly-spaced points on [1,2]

% Initialize the coefficients of the tridiagonal system for N=10
A10 = zeros(N10+1, N10+1);
b_vec10 = zeros(N10+1, 1);

% Apply boundary conditions for N=10
A10(1, 1) = 1; b_vec10(1) = alpha; % u(a) = alpha
A10(N10+1, N10+1) = 1; b_vec10(N10+1) = beta; % u(b) = beta

% Fill the matrix 
for i = 2:N10
    xi = x10(i);
    P_i = P(xi);
    Q_i = Q(xi);
    f_i = f(xi);

    A10(i, i-1) = 1 - h10/2 * P_i;
    A10(i, i) = -2 + h10^2 * Q_i;
    A10(i, i+1) = 1 + h10/2 * P_i;
    b_vec10(i) = h10^2 * f_i;
end

% Solve the system for N=10
u10 = A10 \ b_vec10;

% Plot for N = 10
figure;
plot(x10, u10, 'r-o', 'DisplayName', 'Numerical, N=10', MarkerSize=2.5);
title('Numerical, N=10');
xlabel('x');
ylabel('u(x)');
legend;
grid on;

% Solve for N = 40
N40= 40;
h40 = (b - a) / N40;
x40 = linspace(a, b, N40+1); % Grid points
% Initialize the coefficients of the tridiagonal system for N=40
A40 = zeros(N40+1, N40+1);
b_vec40 = zeros(N40+1, 1);
% Apply boundary conditions for N=40
A40(1, 1) = 1; b_vec10(1) = alpha; % u(a) = alpha
A40(N40+1, N40+1) = 1; b_vec40(N40+1) = beta; % u(b) = beta
% Fill the matrix 
for i = 2:N40
    xi = x40(i);
    P_i = P(xi);
    Q_i = Q(xi);
    f_i = f(xi);

    A40(i, i-1) = 1 - h40/2 * P_i;
    A40(i, i) = -2 + h40^2 * Q_i;
    A40(i, i+1) = 1 + h40/2 * P_i;
    b_vec40(i) = h40^2 * f_i;
end

% Solve the system for N=40
u40 = A40 \ b_vec40;
% Plot for N = 40 with Exact Solution
figure;
plot(x40, u40, 'r-o', 'DisplayName', 'Numerical N=40', MarkerSize=2.5);
hold on;
x_exact = linspace(a, b, 1000);
plot(x_exact, u_exact(x_exact), 'k-', 'DisplayName', 'Exact Solution');
title('Numerical Solution for N=40 with Exact Solution');
xlabel('x');
ylabel('u(x)');
legend;
grid on;
hold off;