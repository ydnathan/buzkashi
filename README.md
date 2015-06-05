# buzkashi
test commit
intuitive ride sharing for corporate commuters

App to broadcast that you are leaving office now to subscribers to a location (publisher subscriber model) 

User should be able to change to a ride_giver at any moment by registering his vehicle.

User will be verified by sending email to his company email. The same code would be stored in session and has to be entered / clicked on. Phone number should also be validated somehow.

Ride-Givers can always mention if itâs a women-only car

Ride-Givers can always share their Ola / Uber taxi the same way
APIs:
[POST] User Registration API.
REQUEST
company_id
user_name
gender
company_email
contact_number
ride_giver?
destination_area_id
(comma separated destinations being covered) saved as a destination_route_id
vehicle_capacity
vehicle_number
profile_image_url
RESPONSE
user_id (of the user created)
[GET] Available Company Locations API
REQUEST
city_code (default=KA_BLR)
RESPONSE (list)
company_address_id
company_lat_lon
company_address_text
[GET] Available Destination Locations API
REQUEST
city_code (default=KA_BLR)
RESPONSE (list)
destination_area_id
destination_area_text
[POST] Create new route
REQUEST
company_address_id
destination_area_ids [comma separated list, ordered]
RESPONSE (list)
destination_route_id
[POST] Broadcast departure
REQUEST
user_id
company_address_id
destination_area_id
destination_route_id
RESPONSE
ride_id
state (default=created)
[GET] Search ride_givers
REQUEST
user_id
company_address_id
destination_area_id
RESPONSE (list)
ride_id
user_id
user_name
vehicle_capacity
seat_available
when?
[POST] Request for a ride
REQUEST
user_id
ride_id
RESPONSE
request_id
state (default=pending)
[GET] (Push) Show list of ride-seekers (if > 0)
REQUEST
user_id
ride_id
RESPONSE (list)
user_id
user_name
destination_area_id
profile_image_url
status
[POST] Approve a ride-seeker
REQUEST
user_id
[user_ids] (ride seekers)
no_more
RESPONSE
user_id
user_name
profile_image_url
contact_number
[GET] Get ride-request status
REQUEST
user_id
[request_id]
RESPONSE (list)
request_id
status (accepted/rejected/pending)
ride_id
user_id (if accepted)
user_name (if accepted)
destination_area_id (if accepted)
destination_route_id (if accepted)
vehicle_number (if accepted)
contact_number (if accepted)
[POST] Confirm ride
REQUEST
user_id
ride_id
final_status (lets_ride / cancel_ride)
RESPONSE
status (success)
[GET] My Rides
REQUEST
user_id
kind (given/taken/all)
RESPONSE (list)
given/taken
user_id
user_name
date_time_of_travel
destination_area_id


DB Design:
users
id
company_id
name
gender
company_email
contact_number
ride_giver?
vehicle_capacity
vehicle_number
profile_image_url
verified
destinations
id
area_code (displayable)
area_text
area_city
area_state
companies
id
address_code (displayable)
email_domain
address_text
address_lat_lon
address_city
address_state
routes
id
name (displayable)
route_destination_map
route_id
destination_id
sequence_number
rides
id
user_id 
route_id
available?
leaving_at
status
created_at
updated_at
requests
id
ride_id
user_id (of the rider)
status (accepted/rejected)
created_at
updated_at









