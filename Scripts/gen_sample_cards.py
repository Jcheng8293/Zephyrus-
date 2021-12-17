from PIL import Image, ImageFont, ImageDraw


def main():
    font_height_pixels = HEIGHT_IMG // 4
    font = ImageFont.truetype('./timesnewroman.ttf', int(font_height_pixels * 0.75))  # 1 px = 0.75 pt
    for i in range(1, NUM_CARDS+1):
        print(f"\r {int(i/NUM_CARDS * 100)}% complete", end='       ')
        img = Image.new('RGB', (WIDTH_IMG, HEIGHT_IMG), BACKGROUND_RGB)
        draw = ImageDraw.Draw(img)
        # specified font size
        text = str(i)
        text_width, text_height = draw.textsize(text, font=font)
        draw.text(((WIDTH_IMG - text_width)//2, (HEIGHT_IMG - text_height) // 2), text,
                  font=font, align="center", fill=FOREGROUND_RGB)
        img.save(f'../Zephyrus/app/src/main/assets/SampleTarotCards/card{i}.png')


NUM_CARDS = 78
BACKGROUND_RGB = (230, 230, 230)
FOREGROUND_RGB = (0, 0, 0)
WIDTH_IMG, HEIGHT_IMG = 219, 304
if __name__ == '__main__':
    main()
