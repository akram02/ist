from bs4 import BeautifulSoup

file = open('ece.html') # converted by https://www.pdftohtml.net/
soup = BeautifulSoup(file.read(), 'html.parser')
lines = soup.text

lineList = lines.splitlines()
print(lines)
# print(lineList[1000])
for f in lines.splitlines():
    # print(f)
    if f.__contains__("Course"):
        # print(f)
        pass