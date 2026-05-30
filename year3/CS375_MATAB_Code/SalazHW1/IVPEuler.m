%Eulrers Method: Xn+1 = Xn + delta(t)*f(tn,Nn)
r = 0.05; 
K = 1000;
%step represent delta(t) in the model
step = .1;%step = .01; %step = .001;
t = 0:step:200;
x_euler = zeros(1, length(t)); %vector for storing results
N01 = 100; N02 = 750; N03 = 1300;

x_euler(1) = N0;
for i = 1:length(t)-1
    %method implemt
    x_euler(i+1) = x_euler(i)+step*r*x_euler(i) * (1-x_euler(i)/K);
end
figure;
plot(t,x_euler);
hold on;
x_euler(1) = N02;
for i = 1:length(t)-1
    %method implemt
    x_euler(i+1) = x_euler(i)+step*r*x_euler(i) * (1-x_euler(i)/K);
end
plot(t,x_euler);
hold on;
x_euler(1) = N03;
for i = 1:length(t)-1
    %method implemt
    x_euler(i+1) = x_euler(i)+step*r*x_euler(i) * (1-x_euler(i)/K);
end
plot(t,x_euler);
hold on;
legend('Initial Populatoin = 100','Initial Populatoin = 750', ...
    'Initial Populatoin = 1300') 