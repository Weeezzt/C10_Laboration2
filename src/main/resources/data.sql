#
# INSERT INTO category (id, name, description, symbol)
# VALUES
#     (1, 'Parks', 'Green public spaces', '🌳'),
#     (2, 'Museums', 'Cultural and historical exhibits', '🏛️'),
#     (3, 'Restaurants', 'Places to eat and dine', '🍴'),
#     (4, 'Shopping Malls', 'Large retail centers', '🛍️');
#
# INSERT INTO user (id, username, password)
# VALUES
#     (1, 'john_doe', 'password123'),
#     (2, 'jane_smith', 'securePass!'),
#     (3, 'admin_user', 'admin@2024'),
#     (4, 'guest_user', 'guest123');
#
# INSERT INTO location (id, name, status, description, coordinate, user_id, category_id)
# VALUES
#     (1, 'Central Park', 'PUBLIC', 'A large public park in New York City.', ST_GeomFromText('POINT(40.785091 -73.968285)', 4326), 1, 1),
#     (2, 'The Louvre', 'PUBLIC', 'Famous art museum in Paris.', ST_GeomFromText('POINT(48.860611 2.337644)', 4326), 2, 2),
#     (3, 'Joe\'s Diner', 'PRIVATE', 'A cozy local restaurant.', ST_GeomFromText('POINT(34.052235 -118.243683)', 4326), 3, 3),
#     (4, 'Mall of America', 'PUBLIC', 'A massive shopping mall in Minnesota.', ST_GeomFromText('POINT(44.854865 -93.242215)', 4326), 4, 4);

SELECT *
FROM location l
WHERE ST_Distance_Sphere(l.coordinate, ST_GeomFromText('POINT(5 5)', 4326)) <= 500000000;
