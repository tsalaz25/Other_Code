%Tomas Salaz Math/CS375 Numerical Methods
%HW4 due 10/18

f = @(x) 1./(1 + x.^2);                      %Runge Function f(x)
%% b) G10(x) using Chebyshev nodes
chebPoints = 5 * cos((2*(0:n)+1)*pi/(2*n+2));  %ChebyShev Nodes on [-5,5]
gx = f(chebPoints);                            %Function Vals at Cheb Nodes
cheb_coeffs = Coef(n, chebPoints, gx);              %Coeffs
%ChebyShev Evaluation
G10 = arrayfun(@(t) Eval(n, chebPoints, cheb_coeffs, t), x_fine);

figure;
plot(points, f(chebPoints), 'b', 'LineWidth', 2); hold on;
plot(points, gx, 'ro', 'MarkerFaceColor', 'b', 'MarkerSize', 5);

legend('G10(x)');
title('G10x Interpolation');
xlim([-5 5]);
ylim([-0.5 2]);
grid on;