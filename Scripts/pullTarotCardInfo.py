from selenium import webdriver # pip install selenium
import os
import re
from urllib import request
from html import unescape

if __name__ == '__main__':
    url = "https://tarotculture.com/list-of-tarot-cards-with-pictures/"
    page = str(request.urlopen(url).read())
    start_index = page.index('''id="major-arcana-list-of-tarot-cards-with-pictures"''')
    end_index = page.index('''id="conclusion"''')
    page = page[start_index:end_index]

    # <h3 id="1-the-magician-tarot-card" class="joli-heading">1. The Magician Tarot card&nbsp;</h3>

    # <h4 id="ace-of-wands-tarot-card" class="joli-heading">Ace of Wands Tarot card&nbsp;</h4>

    # card_name_regex = r'''<h3 id=(.+?) class="joli-heading">(\d+)\.\s+(.+?)(&nbsp;)?</h3>'''
    card_name_regex = r'''<h(3|4) id=(.+?) class="joli-heading">(\d+)?\.?\s*(.+?)(&nbsp;)?</h(3|4)>'''
    card_name_matches = re.findall(card_name_regex, page)

    card_names = [card_name[3] for card_name in card_name_matches if "Suit" not in card_name[3]]

    assert len(card_names) == 78

    list_matches = re.findall(r"<ul>(.+?)</ul>", page)

    # sanity check
    assert len(list_matches) == 156
    for match in list_matches:
        if match.count("<li>") != 3 or match.count("</li>") != 3:
            print("Bad match:", match)

    meanings = []
    for list_match in list_matches:
        match = re.match(r"<li>(.+?)</li>" * 3, list_match)
        meanings += [match.groups()]

    assert len(meanings) == 156

    lines = []

    for card_index, meaning_index in zip(range(len(card_names)), range(0, len(meanings), 2)):
        lines.append(card_names[card_index])
        for meaning in meanings[meaning_index]:
            m = unescape(meaning)
            lines.append(m)
        for meaning in meanings[meaning_index+1]:
            m = unescape(meaning)
            lines.append(m)

    with open("tarot_cards.txt", "w+", encoding='utf-8') as f:
        f.write("\n".join(lines))
