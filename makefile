KEY := ~/.ssh/joelso.pem
HOST := ec2-54-247-57-122.eu-west-1.compute.amazonaws.com

default:
	@echo "Enter deploy option"
	
deploy:
	@./check.sh
	git push
	ssh -i $(KEY) -t ec2-user@$(HOST) "sudo /root/bin/deploy-brains3.sh"
	@ssh -i $(KEY) -t ec2-user@$(HOST) "sudo tail -f -n 50 /home/web/logs/a-brain-for-s3/application.log"