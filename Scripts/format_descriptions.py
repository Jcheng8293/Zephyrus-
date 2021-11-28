import os
from mylib.myterminal import clear_screen
import re

def main():
    filename = os.path.join(os.path.dirname(__file__), 'tarot_cards_description.txt')
    text = open(filename, 'r', encoding='utf-8').read()
    raw_descriptions = text.split('\n\n')

    for d in raw_descriptions:
        d_lines = d.splitlines()
        header = d_lines[0]
        description = ''.join(d_lines[1:])


if __name__ == '__main__':
    # help(str.split)
    main()
