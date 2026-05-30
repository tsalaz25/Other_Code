%Tomas Salaz Math/CS375 Numerical Methods
%HW4 due 10/18

f = @(x) 1./(1 + x.^2);                      %Runge Function f(x)
x_fine = linspace(-5, 5, 1000);              %Fine Mesh Plotting

%% a) P10(x) on [-5,5] using 10 Points
n = 10;
points = linspace(-5, 5, n+1);               %Points to Eval at
px = f(points);                              %Funtion Vals at Points
coeffs = Coef(n, points, px);                %Coeffs
%Plynomial Evaluation
P10 = arrayfun(@(t) Eval(n, points, coeffs, t), x_fine);
%% b) G10(x) using Chebyshev nodes
chebPoints = 5 * cos((2*(0:n)+1)*pi/(2*n+2));  %ChebyShev Nodes on [-5,5]
gx = f(chebPoints);                            %Function Vals at Cheb Nodes
cheb_coeffs = Coef(n, chebPoints, gx);         %Coeffs
%ChebyShev Evaluation
G10 = arrayfun(@(t) Eval(n, chebPoints, cheb_coeffs, t), x_fine);
%% c) Plotting f(x), P10(x), and G10(x)
figure;
plot(x_fine, f(x_fine), 'k', 'LineWidth', 2); hold on;
plot(x_fine, P10, 'r:', 'LineWidth', 2);
plot(x_fine, G10, 'b:', 'LineWidth', 2);

plot(points, px, 'ro', 'MarkerFaceColor', 'r', 'MarkerSize', 5);
plot(chebPoints, gx, 'bo', 'MarkerFaceColor', 'b', 'MarkerSize', 5);

legend('f(x)', 'P10(x)', 'G10(x)');
title('Runge Function and Interpolations');
grid on;

%% d) G20(x), G40(x), G60(x) using Loops
degrees = [20, 40, 60];
colors = ['r', 'y', 'b'];
figure;
%Same Code from above for new interpolations
for i = 1:length(degrees)
    n = degrees(i);
    chebPoints = 5 * cos((2*(0:n)+1)*pi/(2*n+2));  
    gx = f(chebPoints);  
    a_cheb = Coef(n, chebPoints, gx); 
    G = arrayfun(@(t) Eval(n, chebPoints, a_cheb, t), x_fine);
    plot(x_fine, G, 'Color', colors(i), 'LineWidth', 1); hold on;
end
title('Chebyshev Interpolations');
xlim([-5 5]);
ylim([-0.5 2]);
grid on;
legend('G20(x)' , 'G40(x)','G60(x)');