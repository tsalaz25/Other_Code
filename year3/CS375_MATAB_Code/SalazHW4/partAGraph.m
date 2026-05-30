%Tomas Salaz Math/CS375 Numerical Methods
%HW4 due 10/18

f = @(x) 1./(1 + x.^2);                      %Runge Function f(x)
%% a) P10(x) on [-5,5] using 10 Points
n = 10;
points = linspace(-5, 5, n+1);               %Points to Eval at
px = f(points);                              %Funtion Vals at Points
coeffs = Coef(n, points, px);                %Coeffs
%Plynomial Evaluation
P10 = arrayfun(@(t) Eval(n, points, coeffs, t), points);

figure;
plot(points, f(points), 'k', 'LineWidth', 2); hold on;
plot(points, px, 'ro', 'MarkerFaceColor', 'r', 'MarkerSize', 5);

legend('P10(x)');
title('P10x Interpolation');
xlim([-5 5]);
ylim([-0.5 2]);
grid on;