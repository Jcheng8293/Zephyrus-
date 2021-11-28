import os
from mylib.myterminal import clear_screen
import re


def main():
    filename = os.path.join(os.path.dirname(__file__), 'tarot_cards_description.txt')
    with open(filename, 'r', encoding='utf-8') as f_in:
        text = f_in.read()
    raw_descriptions = text.split('\n\n')

    f_out = open('tarot_descriptions.txt', 'w+')
    for i, d in enumerate(raw_descriptions):
        d_lines = d.splitlines()
        header = d_lines[0]
        description = ''.join(d_lines[1:])
        f_out.write(header)
        f_out.write('\n')
        f_out.write(description)
        if i != len(raw_descriptions) - 1:
            f_out.write('\n')
    f_out.close()

if __name__ == '__main__':
    # help(str.split)
    main()
