#!/bin/bash

while true
do
	clear
	echo ""
	echo " ██████╗ ███████╗███╗   ██╗██╗ █████╗ ██╗          ██████╗ ███████╗    ███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗"
	echo " ██╔══██╗██╔════╝████╗  ██║██║██╔══██╗██║         ██╔═══██╗██╔════╝    ██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝"
	echo " ██║  ██║█████╗  ██╔██╗ ██║██║███████║██║         ██║   ██║█████╗      ███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  "
	echo " ██║  ██║██╔══╝  ██║╚██╗██║██║██╔══██║██║         ██║   ██║██╔══╝      ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  "
	echo " ██████╔╝███████╗██║ ╚████║██║██║  ██║███████╗    ╚██████╔╝██║         ███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗"
	echo " ╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═╝         ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝"
	echo ""
	echo " ##########################################################################################################################"
	echo " # Before running, please setup your simulation by editing: /src/test/resources/simulation.conf                           #"
	echo " ##########################################################################################################################"
	echo ""
	echo " Please choose an action (enter number):"
	echo ""
	echo " 1: Run HttpGetRequestSimulation"
	echo " 2: Run HttpPostRequestSimulation"
	echo " 3: Exit"
	read -p ' : ' action
	if [[ $action -eq 1 ]] 
	then
		clear
		echo " Running HttpGetRequestSimulation..."
		sleep 1
		mvn clean gatling:test -Dgatling.simulationClass=se.sundsvall.dos.HttpGetRequestSimulation
	elif [[ $action -eq 2 ]] 
	then
		clear
		echo " Running HttpPostRequestSimulation..."
		sleep 1
		mvn clean gatling:test -Dgatling.simulationClass=se.sundsvall.dos.HttpPostRequestSimulation
	elif [[ $action -eq 3 ]] 
	then
		clear
		break
	else
		clear
		echo " Please choose a valid action!"
		sleep 3
	fi
done
