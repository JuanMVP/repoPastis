import requests 

response = requests.get("http://api.open-notify.org/iss-now.json")

# Print the status code of the response.
print(response.status_code)
print(response.json())
response.headers['content-type']

response.encoding

response.text
response.json()

