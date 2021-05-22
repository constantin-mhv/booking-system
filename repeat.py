import os
import sys

def main():
    if len(sys.argv) != 2:
        print("ERROR: A single argument required!")
        return
    print("Repeating command \"" + sys.argv[1] + "\".")
    
    while (True):
        inp = sys.stdin.readline()
        if (inp[0] == ' '):
            s = sys.argv[1] + " " + inp[1:]
        else:
            s = inp
        #print(s)
        os.system(s)

if __name__ == "__main__":
    main()