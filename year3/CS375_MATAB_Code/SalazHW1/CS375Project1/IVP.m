r = 0.05; 
K = 1000;  
model = @(t, N) r * N * (1 - N / K);

tspan = [0 200];
N01 = 100; N02 = 750; N03 = 1300;
figure;
[t, N] = ode45(model, tspan, N01);
plot(t, N);
hold on;
[t, N] = ode45(model, tspan, N02);
plot(t, N);
hold on; 
[t, N] = ode45(model, tspan, N03);
plot(t, N);
hold on;
legend('Initial Populatoin = 100','Initial Populatoin = 750', ...
    'Initial Populatoin = 1300') 