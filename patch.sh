. ../../session.sh
PATH=../bin/:$PATH
rm -f -r log2 log2.rtf send2.bak send

. ../../functions.sh

bash  clean.sh

# ----------------------------------------------------------------------------



cat <<+END+ > text.dat
2
3
+END+

compile catalogue/Basket.java

sh to_rtf6.sh log2
cp log2.rtf /cygdrive/c/mac

exit

# =============================================================================

