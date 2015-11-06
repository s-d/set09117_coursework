for (int i=0; i < maxCustomers; i++){
	for (int j=0; j < maxCustomers; j++){
		if (i != j){
		    double saving= (depot.distance(ci)+depot.distance(cj)) -ci.distance(cj) ;
				savings.add(new SavingsNode(ci,cj,saving));
		}
                
	}
            
}
	savings.sort()

	for(Each SavingsNode sn in savings){
        //Neither customers are in a route already
            if (sn.from & sn.to not already in routes){
                if (sn.from.requirement+sn.to.requirement <= capacity){
                    Route newR = new Route();
                    newR.add(sn.from);
					newR.add(sn.to);
				}
            }else
				//Find a route that ends at 'from'
                if (sn.to not in a route ){
                    for(Route route: routes){
                        if (route.lastDelivery()== sn.from){
                            if (route.hasCapacity(sn.to.requirement)){
                                route.add(sn.to);
								break;
                            }
                        }
                    }
                }

				
			//Find a route that starts at 'to'
            if (sn.from not in a route){
                for(Route route: routes){
                    if (route.lastDelivery() == sn.to){
                        if (route.hasCapacity(sn.from.requirement)){
                            route.addToStart(sn.from);
							break;
                        }
                    }
                }
            }

			//Check for the case of two routes that can be merged
            Route merged=null;
            for(Route routeX: routes){
                if (merged != null)
					break;
                if (routeX.lastDelivery() == sn.from){
                    for(Route routeY: routes){
                        if (routeY.first() == sn.to){
                            if (routeX != routeY){
								if ((routeX.goods() + routeY.goods())<= capacity){
									routeX.merge(routeY);
									merged= routeY;
									break;
								}
                            }
                        }
                    }
                }
            }
            if (merged != null)
                routes.remove(merged);
                
        }

			
		//Now allocate any remaining custs to individial routes
        while(deliveries.size()>0){
            Customer c = deliveries.remove();
            Route r = new Route();
            r.add(c);
	}
