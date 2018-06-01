// Add Several Tag To  New Array 

db.customers.update({ $addToSet : { phone : 989898944 }});
// Add phone To Array  if the value is not in the array already

db.customers.update({ $pull : {phone:9898944}});
//removes value from an existing array

db.customers.update({ $pop : { phone : 1}});
//operator removes the first or last element of an array. Pass $pop a value of 1 to remove the last element in an array and a value of -1 to remove the first element of an array

db.cutomers.update( {member:true} , {$rename : {'member' : 'membership'}} );
//updates the name of a field


 	 	