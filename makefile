HEROKU_PROD_APP := scarf-prod
HEROKU_STAGING_APP := scarf-staging
LOCAL_DB := scarf


default:
	@echo "Enter deploy option"
	
deploy:
	@./check.sh
	git push $(HEROKU_STAGING_APP) master
	heroku logs --tail --app $(HEROKU_STAGING_APP)

deploy-prod:
	@./check.sh
	heroku pgbackups:capture --expire --app $(HEROKU_PROD_APP)
	git push $(HEROKU_PROD_APP) master
	heroku logs --tail --app $(HEROKU_PROD_APP)  

staging-local:
	play -Dconfig.resource=staging-local.conf start
	
db-reset:
	@echo "Dropping all connections to database $(LOCAL_DB)"
	@psql $(LOCAL_DB) -U postgres -c "SELECT pg_terminate_backend(pg_stat_activity.procpid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$(LOCAL_DB)' and procpid <> pg_backend_pid();" > /dev/null
	@dropdb $(LOCAL_DB) -U postgres
	@createdb $(LOCAL_DB) -U postgres
	@echo "Database was emptied and recreated"
	
db-dump: 
	@echo "Dumping $(HEROKU_PROD_APP) db -> $(LOCAL_DB)..."
	@heroku pgbackups:capture --expire --app $(HEROKU_PROD_APP)
	@dropdb $(LOCAL_DB) -U postgres
	@createdb $(LOCAL_DB) -U postgres
	@curl -o /tmp/latest.dump `heroku pgbackups:url --app $(HEROKU_PROD_APP)`
	@pg_restore --verbose --clean --no-acl --no-owner -U postgres -d $(LOCAL_DB) /tmp/latest.dump
