KEY := ~/.ssh/joelso.pem
HOST := ec2-54-247-57-122.eu-west-1.compute.amazonaws.com

default:
	@echo "Enter deploy option"
	
deploy:
	@./check.sh
	git push
	ssh -i $(KEY) -t ec2-user@$(HOST) "sudo /root/bin/deploy-brains3.sh"
