function [output] = pendulum_f(t,Theta)
%right hand side function for pendulum problem
output = [Theta(2) -sin(Theta(1))]';
end
