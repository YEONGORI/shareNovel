import 'package:app/models/novel_model.dart';
import 'package:app/services/api_service.dart';
import 'package:app/widgets/novel_widget.dart';
import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  HomeScreen({super.key});

  final Future<List<NovelModel>> novels = ApiService.getTodaysNovel();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: const Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Logo',
              style: TextStyle(fontSize: 22, fontWeight: FontWeight.w400),
            ),
          ],
        ),
        foregroundColor: Colors.amber,
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: FutureBuilder(
        future: novels,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return Column(
              children: [
                Expanded(
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: 10,
                    itemBuilder: (context, index) {
                      var novel = snapshot.data![index];
                      return Novel(
                        title: novel.title,
                        plot: novel.plot,
                        thumb: novel.thumb,
                        id: novel.id,
                      );
                    },
                  ),
                )
              ],
            );
          }
          return const Center(
            child: CircularProgressIndicator(),
          );
        },
      ),
      // bottomNavigationBar: BottomNavigationBar(
      //   items: const [],
      // ),
    );
  }
}
