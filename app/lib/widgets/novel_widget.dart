import 'package:app/screens/novel_detail_screen.dart';
import 'package:flutter/material.dart';

class Novel extends StatelessWidget {
  final String title, plot, thumb, id;

  const Novel({
    super.key,
    required this.title,
    required this.plot,
    required this.thumb,
    required this.id,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => const NovelDetailScreen(
              title: "title",
              thumb: "thumb",
              id: "id",
            ),
            fullscreenDialog: true,
          ),
        );
      },
      child: Column(
        children: [
          Hero(
            tag: id,
            child: Container(
              // clipBehavior: Clip.antiAlias,
              decoration: BoxDecoration(
                boxShadow: [
                  BoxShadow(
                      blurRadius: 2,
                      offset: const Offset(8, 8),
                      color: Colors.black.withOpacity(0.5))
                ],
              ),
              child: Image.network(
                thumb,
                headers: const {
                  "User-Agent":
                      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
                },
              ),
            ),
          ),
          Text(
            title,
          ),
          Text(
            plot,
          ),
        ],
      ),
    );
  }
}
