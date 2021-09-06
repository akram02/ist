from bs4 import BeautifulSoup

file = open('ece.html') # converted by https://www.pdftohtml.net/
soup = BeautifulSoup(file.read(), 'html.parser')
lines = soup.text
print(lines)
