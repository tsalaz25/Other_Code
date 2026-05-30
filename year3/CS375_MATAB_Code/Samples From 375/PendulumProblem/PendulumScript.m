%-------------------
%Pendulum script 
clear All
clc

%initial and final time configuration
Tf=4*pi;
t_0=0;
%size of timestep
h=.1;
%time iterations
iter=(Tf-t_0)/h;

t=t_0;
%initial condition - case 1.
Initial_Condition=[0.6;0];
Theta_old=Initial_Condition;
Num_Sol(:,1)=Theta_old;

Num_Sol;

for i=1:iter
    
    K1=h*pendulum_f(t,Theta_old);
    K2=h*pendulum_f(t+0.5*h,Theta_old+0.5*K1);
    K3=h*pendulum_f(t+0.5*h,Theta_old+0.5*K2);
    K4=h*pendulum_f(t+h,Theta_old+K3);
    Theta_new=Theta_old+(1/6)*(K1+2*K2+2*K3+K4);
    
    %solution storing
    Num_Sol(:,i+1)=Theta_new;
    
    %updates
    Theta_old=Theta_new;
    t=t+h;
end
%recall that first row in Numer_Sol gives theta(t) and second row gives
% theta'(t).
t=[t_0:h:Tf];
theta=Num_Sol(1,:);

%small-angle approximation
sa_approx=Initial_Condition(1)*cos(t)+Initial_Condition(2)*sin(t);

figure(10)
plot(t,theta,'.-',t,sa_approx,'o')
axis([0 4*pi min(min(theta,sa_approx)) max(theta)])
legend('Numerical Solution RK4','Small-Angle-Approx')
ylabel('\theta')
xlabel('t')
