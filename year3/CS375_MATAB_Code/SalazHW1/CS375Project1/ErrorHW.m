clc
format long
int = -pi: .01 : pi;
x = cos(int);
p2x = 1-int.^2/2;
p4x = 1-int.^2/2 + int.^4/24;

figure(1);
plot(int, x, int, p2x, int, p4x);
legend('f(x)', 'P2(x)', 'P4(x)');
grid on;

r2x = @(x, h) (x.^3 .* (sin(x))) / 6 * h;
r4x = @(x, h) (x.^5 .* (-sin(x))) / 120 * h;
h1 = 0.1; h2 = 0.01; h3 = 0.001;
R2_h1 = r2x(int, h1); R2_h2 = r2x(int, h2); R2_h3 = r2x(int, h3);
R4_h1 = r4x(int, h1); R4_h2 = r4x(int, h2); R4_h3 = r4x(int, h3);

figure(2);
plot(int, R2_h1, int, R2_h2, int, R2_h3, int, R4_h1, int, R4_h2, int, R4_h3);
legend('R2(x=0.1)', 'R2(x=0.01)', 'R2(x=0.001)', 'R4(x=0.1)', ...
    'R4(x=0.01)', 'R4(x=0.001)');
grid on;